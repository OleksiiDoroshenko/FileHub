import Action from '../action.js';

export default class LogOutAction extends Action {

  apply(stateManager, apiService) {
    apiService.logOut().then(() => {
      window.location.hash = '#/login';
    });
  }
}
