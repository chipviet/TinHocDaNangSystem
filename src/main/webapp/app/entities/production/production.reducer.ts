import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProduction, defaultValue } from 'app/shared/model/production.model';

export const ACTION_TYPES = {
  FETCH_PRODUCTION_LIST: 'production/FETCH_PRODUCTION_LIST',
  FETCH_PRODUCTION: 'production/FETCH_PRODUCTION',
  CREATE_PRODUCTION: 'production/CREATE_PRODUCTION',
  UPDATE_PRODUCTION: 'production/UPDATE_PRODUCTION',
  DELETE_PRODUCTION: 'production/DELETE_PRODUCTION',
  RESET: 'production/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProduction>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ProductionState = Readonly<typeof initialState>;

// Reducer

export default (state: ProductionState = initialState, action): ProductionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRODUCTION):
    case REQUEST(ACTION_TYPES.UPDATE_PRODUCTION):
    case REQUEST(ACTION_TYPES.DELETE_PRODUCTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTION):
    case FAILURE(ACTION_TYPES.CREATE_PRODUCTION):
    case FAILURE(ACTION_TYPES.UPDATE_PRODUCTION):
    case FAILURE(ACTION_TYPES.DELETE_PRODUCTION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRODUCTION):
    case SUCCESS(ACTION_TYPES.UPDATE_PRODUCTION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRODUCTION):
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

const apiUrl = 'api/productions';

// Actions

export const getEntities: ICrudGetAllAction<IProduction> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTION_LIST,
    payload: axios.get<IProduction>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IProduction> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTION,
    payload: axios.get<IProduction>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProduction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRODUCTION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProduction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRODUCTION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProduction> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRODUCTION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
