import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGuaranteeProduction, defaultValue } from 'app/shared/model/guarantee-production.model';

export const ACTION_TYPES = {
  FETCH_GUARANTEEPRODUCTION_LIST: 'guaranteeProduction/FETCH_GUARANTEEPRODUCTION_LIST',
  FETCH_GUARANTEEPRODUCTION: 'guaranteeProduction/FETCH_GUARANTEEPRODUCTION',
  CREATE_GUARANTEEPRODUCTION: 'guaranteeProduction/CREATE_GUARANTEEPRODUCTION',
  UPDATE_GUARANTEEPRODUCTION: 'guaranteeProduction/UPDATE_GUARANTEEPRODUCTION',
  DELETE_GUARANTEEPRODUCTION: 'guaranteeProduction/DELETE_GUARANTEEPRODUCTION',
  RESET: 'guaranteeProduction/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGuaranteeProduction>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GuaranteeProductionState = Readonly<typeof initialState>;

// Reducer

export default (state: GuaranteeProductionState = initialState, action): GuaranteeProductionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GUARANTEEPRODUCTION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GUARANTEEPRODUCTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GUARANTEEPRODUCTION):
    case REQUEST(ACTION_TYPES.UPDATE_GUARANTEEPRODUCTION):
    case REQUEST(ACTION_TYPES.DELETE_GUARANTEEPRODUCTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GUARANTEEPRODUCTION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GUARANTEEPRODUCTION):
    case FAILURE(ACTION_TYPES.CREATE_GUARANTEEPRODUCTION):
    case FAILURE(ACTION_TYPES.UPDATE_GUARANTEEPRODUCTION):
    case FAILURE(ACTION_TYPES.DELETE_GUARANTEEPRODUCTION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GUARANTEEPRODUCTION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GUARANTEEPRODUCTION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GUARANTEEPRODUCTION):
    case SUCCESS(ACTION_TYPES.UPDATE_GUARANTEEPRODUCTION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GUARANTEEPRODUCTION):
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

const apiUrl = 'api/guarantee-productions';

// Actions

export const getEntities: ICrudGetAllAction<IGuaranteeProduction> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GUARANTEEPRODUCTION_LIST,
    payload: axios.get<IGuaranteeProduction>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGuaranteeProduction> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GUARANTEEPRODUCTION,
    payload: axios.get<IGuaranteeProduction>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGuaranteeProduction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GUARANTEEPRODUCTION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGuaranteeProduction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GUARANTEEPRODUCTION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGuaranteeProduction> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GUARANTEEPRODUCTION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
