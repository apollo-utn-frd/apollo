
export interface AuthState {
  id: string;
  token: string;
}

export const INITIAL_AUTH_STATE: AuthState
  = { id: undefined
  , token: undefined
};
