import VerificationError from '../../../models/errors/verification-error';
import AuthorizationError from '../../../models/errors/authorization-error';
import ServerValidationErrors from '../../../models/errors/server-validation-errors';
import NotFoundError from '../../../models/errors/not-found-error';

/**
 * Implements login and registration methods logic.
 */
export default class ApiService {
  /**
   * Sends request yo the server for user log in.
   * @param {UserData} userData - instance of {@link UserData}.
   * @return {Promise<Response>} if user with this login and password is already registered then
   * method resolve contains welcoming alert.
   * If user is not registered than method reject returns {@link AuthorizationError}.
   */
  logIn(userData) {
    return fetch('/login', {
      method: 'POST',
      body: userData,
    }).then(async (response) => {
      if (response.ok) {
        const result = response.json();
        return await result.then((body) => {
          localStorage.setItem('token', body.token);
        });
      }
      throw await this.getError(response, {type: 'User', name: userData.login});
    });
  }

  /**
   * Sends request to the server for user root folder id.
   * @return {Promise<Response>} if everything is ok returns user root folder id.
   */
  getRoot() {
    return fetch('/folder/root', {
      method: 'GET',
      headers: {
        token: localStorage.getItem('token'),
      },
    }).then(async (response) => {
      if (response.ok) {
        return response.json();
      }
      throw await this.getError(response, {type: 'Folder', name: 'Root'});
    });
  }

  /**
   * Sends request yo the server for user registration.
   * @param {UserData} userData -instance of {@link UserData}.
   * @return {Promise<Response>} if user with this login is already registered then method reject returns
   * {@link AuthorizationError}. If user's is not registered, but his password is not valid method reject returns
   * {@link VerificationError}. If everything is alright method resolve contains redirection to {@link LoginPage}
   */
  register(userData) {
    return fetch('/register', {
      method: 'POST',
      body: userData,
    }).then(async (response) => {
      if (response.ok) {
        return;
      }
      throw await this.getError(response, {type: 'User', name: userData.login});
    });
  }

  /**
   * Returns specific error instance from response code.
   * @param {Response} response - server response.
   * @param {FolderModel|FileModel} model - model of requested item.
   * @return {Promise<AuthorizationError|Error|ServerValidationErrors>}
   */
  async getError(response, model) {
    switch (response.status) {
      case 401: {
        let message = response.statusText;
        await response.text().then((text) => {
          message = text.length > 0 ? text : message;
        });
        return new AuthorizationError(message);
      }
      case 422: {
        const result = response.json();
        const serverErrors = [];
        await result.then((errors) => {
          errors.errors.forEach((error) => {
            serverErrors.push(new VerificationError(error.field, error.message));
          });
        });
        return new ServerValidationErrors(serverErrors);
      }
      case 404: {
        let message = response.statusText;
        await response.text().then((text) => {
          message = text;
        });
        return new NotFoundError(message, model);
      }
      case 500: {
        return new Error(response.statusText);
      }
      default: {
        let message = response.statusText;
        await response.text().then((text) => {
          message = text;
        });
        return new Error(message);
      }
    }
  }

  /**
   * Sends request to the server for getting folder content.
   * @param {FolderModel} model - model of requested item.
   * @return {Promise<Response>}
   */
  getItems(model) {
    return fetch(`/folder/${model.id}/content`, {
      method: 'GET',
      headers: {
        token: localStorage.getItem('token'),
      },
    }).then(async (response) => {
      if (response.ok) {
        return response.json();
      }
      throw await this.getError(response, model);
    });
  }


  /**
   * Sends request to server for uploading new file.
   * @param {FolderModel} model - model of requested item.
   * @param {File} file - file to be loaded.
   * @return {Promise<Response>}
   */
  uploadFile(model, file) {
    return fetch(`/folder/${model.id}/file`, {
      method: 'POST',
      headers: {
        token: localStorage.getItem('token'),
      },
      body: {
        file: file,
      },
    }).then(async (response) => {
      if (response.ok) {
        return 200;
      }
      throw await this.getError(response, model);
    });
  }

  /**
   * Sends request to server for deleting file by its id.
   * @param {FileModel} model - model of requested item.
   * @return {Promise<Response>} server response or error.
   */
  deleteFile(model) {
    return fetch(`/file/${model.id}`, {
      method: 'DELETE',
      headers: {
        token: localStorage.getItem('token'),
      },
    }).then(async (response) => {
      if (response.ok) {
        return 200;
      }
      throw await this.getError(response, model);
    });
  }

  /**
   * Sends request to server for deleting folder by its id.
   * @param {FolderModel} model - model of requested item.
   * @return {Promise<Response>} server response or error.
   */
  deleteFolder(model) {
    return fetch(`/folder/${model.id}`, {
      method: 'DELETE',
      headers: {
        token: localStorage.getItem('token'),
      },
    }).then(async (response) => {
      if (response.ok) {
        return 200;
      }
      throw await this.getError(response, model);
    });
  }

  /**
   * Sends request to server for logging out current user.
   * Regardless of the answer removes current token from local storage.
   * @return {Promise<Response>}
   */
  logOut() {
    return fetch('/logout', {
      method: 'POST', headers: {
        token: localStorage.getItem('token'),
      },
    }).then(async (response) => {
      if (response.ok) {
        return;
      }
      throw await this.getError(response, {type: 'User'});
    }).finally(() => {
      localStorage.removeItem('token');
    });
  }

  /**
   * Sends request to the server for getting file's blob.
   * @param {FileModel} model - file model.
   * @return {Promise<Response>}
   */
  getFile(model) {
    return fetch(`/file/${model.id}`, {
      method: 'GET', headers: {
        token: localStorage.getItem('token'),
      },
    }).then(async (response) => {
      if (response.ok) {
        return response.blob();
      }
      throw await this.getError(response, model);
    });
  }

  /**
   * Sends request to the server for getting user by its token.
   * @return {Promise<Response>}
   */
  getUser() {
    return fetch('/user', {
      method: 'GET', headers: {
        token: localStorage.getItem('token'),
      },
    }).then(async (response) => {
      if (response.ok) {
        return response.json();
      }
      throw await this.getError(response, {type: 'User'});
    });
  }
}
