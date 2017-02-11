
import {User} from "../../models/user";

export interface StoreData {
  currentUser: User
}

export const INITIAL_STORE_DATA: StoreData = {
  currentUser: undefined,
};
