import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICart, defaultValue } from 'app/shared/model/cart.model';

export const ACTION_TYPES = {
  FETCH_CART_LIST: 'cart/FETCH_CART_LIST',
  FETCH_CART: 'cart/FETCH_CART',
  CREATE_CART: 'cart/CREATE_CART',
  UPDATE_CART: 'cart/UPDATE_CART',
  DELETE_CART: 'cart/DELETE_CART',
  RESET: 'cart/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICart>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CartState = Readonly<typeof initialState>;

// Reducer

export default (state: CartState = initialState, action): CartState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CART_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CART):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CART):
    case REQUEST(ACTION_TYPES.UPDATE_CART):
    case REQUEST(ACTION_TYPES.DELETE_CART):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CART_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CART):
    case FAILURE(ACTION_TYPES.CREATE_CART):
    case FAILURE(ACTION_TYPES.UPDATE_CART):
    case FAILURE(ACTION_TYPES.DELETE_CART):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CART_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CART):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CART):
    case SUCCESS(ACTION_TYPES.UPDATE_CART):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CART):
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

const apiUrl = 'api/carts';

// Actions

export const getEntities: ICrudGetAllAction<ICart> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CART_LIST,
    payload: axios.get<ICart>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICart> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CART,
    payload: axios.get<ICart>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICart> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CART,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICart> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CART,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICart> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CART,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
