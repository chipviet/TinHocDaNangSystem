import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBrand, defaultValue } from 'app/shared/model/brand.model';

export const ACTION_TYPES = {
  FETCH_BRAND_LIST: 'brand/FETCH_BRAND_LIST',
  FETCH_BRAND: 'brand/FETCH_BRAND',
  CREATE_BRAND: 'brand/CREATE_BRAND',
  UPDATE_BRAND: 'brand/UPDATE_BRAND',
  DELETE_BRAND: 'brand/DELETE_BRAND',
  RESET: 'brand/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBrand>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type BrandState = Readonly<typeof initialState>;

// Reducer

export default (state: BrandState = initialState, action): BrandState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BRAND_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BRAND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BRAND):
    case REQUEST(ACTION_TYPES.UPDATE_BRAND):
    case REQUEST(ACTION_TYPES.DELETE_BRAND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_BRAND_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BRAND):
    case FAILURE(ACTION_TYPES.CREATE_BRAND):
    case FAILURE(ACTION_TYPES.UPDATE_BRAND):
    case FAILURE(ACTION_TYPES.DELETE_BRAND):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BRAND_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BRAND):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BRAND):
    case SUCCESS(ACTION_TYPES.UPDATE_BRAND):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BRAND):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/brands';

// Actions

export const getEntities: ICrudGetAllAction<IBrand> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BRAND_LIST,
  payload: axios.get<IBrand>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IBrand> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BRAND,
    payload: axios.get<IBrand>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBrand> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BRAND,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBrand> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BRAND,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBrand> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BRAND,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
