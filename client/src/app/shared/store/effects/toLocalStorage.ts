import {User} from "../../models/user";

export function toLocalStorage(userData: {user: User, token: string}) {
  window.localStorage.setItem('id', userData.user.idGoogle);
  window.localStorage.setItem('token', userData.token);
}
