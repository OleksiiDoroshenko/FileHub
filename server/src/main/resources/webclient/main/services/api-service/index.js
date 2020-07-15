import VerificationError from '../../../models/errors/verification-error/index.js';
import AuthorizationError from '../../../models/errors/authorization-error/index.js';
import ServerValidationErrors from '../../../models/errors/server-validation-errors/index.js';
import NotFoundError from '../../../models/errors/not-found-error/index.js';

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
      throw await this.getError(response, 'User');
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
      throw await this.getError(response, 'Folder');
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
      body: JSON.stringify(userData),
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
   * @param {string} requestedItem - requested item from server.
   * @return {Promise<AuthorizationError|Error|ServerValidationErrors>}
   */
  async getError(response, requestedItem) {
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
        return new NotFoundError(message, requestedItem);
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
      throw await this.getError(response, 'Folder');
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
      throw await this.getError(response, 'Folder');
    });
  }

  deleteFile(id) {
    return fetch(`/file/${id}`, {
      method: 'DELETE',
      headers: {
        token: localStorage.getItem('token'),
      },
    }).then(async (response) => {
      if (response.ok) {
        return 200;
      }
      throw await this.getError(response, 'File');
    });
  }

  deleteFolder(id) {
    return fetch(`/folder/${id}`, {
      method: 'DELETE',
      headers: {
        token: localStorage.getItem('token'),
      },
    }).then(async (response) => {
      if (response.ok) {
        return 200;
      }
      throw await this.getError(response, 'Folder');
    });
  }

  /**
   * Sends request for renaming list item.
   * @param {Object} model - item model.
   * @return {Promise<Response>} - server response.
   */
  renameItem(model) {
    return fetch(`/item/${model.id}/`, {
      method: 'PUT',
      headers: {
        token: localStorage.getItem('token'),
      },
      body: {
        name: model.name,
      },
    }).then(async (response) => {
      if (response.ok) {
        return 200;
      }
      throw await this.getError(response, model.type);
    });
  }

  /**
   * Sends request to the server for creating new folder.
   * @param {Object} model - item model in which should be created new folder.
   * @return {Promise<Response>} - server response.
   */
  createFolder(model) {
    return fetch(`/folder/${model.id}/folder`, {
      method: 'POST',
      headers: {
        token: localStorage.getItem('token'),
      },
    }).then(async (response) => {
      if (response.ok) {
        return response.json();
      }
      throw await this.getError(response, model.type);
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
      throw await this.getError(response, 'User');
    }).finally(() => {
      localStorage.removeItem('token');
    });
  }

  /**
   * Sends request to the server for getting file's blob.
   * @param {string} id - file id.
   * @return {Promise<Response>}
   */
  getFile(id) {
    return fetch(`/file/${id}`, {
      method: 'GET', headers: {
        token: localStorage.getItem('token'),
      },
    }).then(async (response) => {
      if (response.ok) {
        return response.blob();
      }
      throw await this.getError(response, 'File');
    });
  }

  getUser() {
    return fetch('/user', {
      method: 'GET', headers: {
        token: localStorage.getItem('token'),
      },
    }).then(async (response) => {
      if (response.ok) {
        return response.json();
      }
      throw await this.getError(response, 'User');
    });
  }

  getFolder(id) {
    return fetch(`/folder/${id}`, {
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
}
