import VerificationError from '../../../models/errors/verification-error';
import AuthorizationError from '../../../models/errors/authorization-error';

import MockServer from '../mock-server';
import ServerValidationErrors from '../../../models/errors/server-validation-errors';
import NotFoundError from '../../../models/errors/not-found-error';

/**
 * Implements login and registration methods logic.
 */
export default class ApiService {
  /**
   * Creates object that intercepts requests for the server.
   */
  constructor(devMod) {
    if (devMod) {
      new MockServer();
    }
  }


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
      throw await this.getError(response);
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
      throw await this.getError(response);
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
      throw await this.getError(response);
    });
  }

  /**
   * Returns specific error instance from response code.
   * @param {Response} response - server response.
   * @return {Promise<AuthorizationError|Error|ServerValidationErrors>}
   */
  async getError(response) {
    switch (response.status) {
      case 401: {
        let message = response.statusText;
        await response.text().then(text => {
          message = text;
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
        await response.text().then(text => {
          message = text;
        });
        return new NotFoundError(message);
      }
      case 500: {
        return new Error(response.statusText);
      }
      default: {
        let message = response.statusText;
        await response.text().then(text => {
          message = text;
        });
        return new Error(message);
      }
    }
  }

  /**
   * Sends request to the server for getting folder content.
   * @param {string} folderId - folder id.
   * @return {Promise<Response>}
   */
  getItems(folderId) {
    return fetch(`/folder/${folderId}/content`, {
      method: 'GET',
      headers: {
        token: localStorage.getItem('token'),
      },
    }).then(async (response) => {
      if (response.ok) {
        return response.json();
      }
      throw await this.getError(response);
    });
  }

  /**
   * Sends request to server for uploading new file.
   * @param {string} parentId - id of parent folder where file will be loaded.
   * @param {File} file - file to be loaded.
   * @returns {Promise<>}
   */
  uploadFile(parentId, file) {
    return fetch(`/folder/${parentId}/file`, {
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
      throw await this.getError(response);
    });
  }
}
