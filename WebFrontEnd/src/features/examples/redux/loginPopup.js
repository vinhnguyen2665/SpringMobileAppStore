import { useCallback } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {SHOW_LOGIN} from './constants';

export function showLoginPopup() {
  return {
    type: SHOW_LOGIN,
  };
}

export function useShowLoginPopup() {
  const dispatch = useDispatch();
  const isShowLogin = useSelector(state => state.examples.isShowLogin);
  const action = useCallback(() => dispatch(showLoginPopup()), [dispatch]);

  return { isShowLogin, showLoginPopup: action };
}

export function reducer(state, action) {
  switch (action.type) {
    case SHOW_LOGIN:
      return {
        ...state,
        isShowLogin: state.isShowLogin ? state.isShowLogin = false : state.isShowLogin = true,
      };

    default:
      return state;
  }
}
