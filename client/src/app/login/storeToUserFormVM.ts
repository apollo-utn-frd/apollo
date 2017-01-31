
import {ApplicationState} from "../shared/store/state/application.state";
import {UserFormVM} from "../shared/models/userForm.vm";
import {User} from "../shared/models/user";

export function storeToUserFormVM(state: ApplicationState): UserFormVM {
  const currentUser = state.storeData.currentUser;
  if (currentUser) {
    return mkUserFormVM(currentUser);
  } else {
    return emptyUserFormVM;
  }
}

function mkUserFormVM(user: User): UserFormVM {
  return {
    nombre: user.nombre,
    apellido: user.apellido,
    username: user.username,
    descripcion: user.descripcion
  }
}

const emptyUserFormVM = {
  nombre: '',
  apellido: '',
  username: '',
  descripcion: ''
};
