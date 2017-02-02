
import {ApplicationState} from "../shared/store/state/application.state";
import {User} from "../shared/models/user";
import * as _ from 'lodash';

export function storeToUserWithAuthVM(state: ApplicationState): UserWithAuthVM {
  const currentUser = state.storeData.currentUser;
  const token = state.authState.token;
  return {user: currentUser, token: token};
}

export interface UserWithAuthVM {
  user: User
  token: string
}
