import VerificationError from '../../../models/errors/verification-error';
import AuthorizationError from '../../../models/errors/authorization-error';


import MockServer from '../mock-server';

/**
 * Implements login and registration methods logic.
 */
export default class AppService {
  /**
   * Creates object that intercepts requests for the server.
   * @param {boolean} devMod - flag that turns on/off mock server.
   */
  constructor(devMod) {
    if (devMod) {
      new MockServer();
    }
  }

  /**
   * Sends request to the server for user login.
   * @param {UserData} userData -instance of {@link UserData}.
   * @return {Promise<Response>} if user with this login and password is already registered then
   * method resolve contains welcoming alert.
   * If user is not registered than method reject returns {@link AuthorizationError}.
   */
  login(userData) {
    return new Promise((resolve, reject) => {
      fetch('/login', {
        method: 'POST',
        body: userData,
      }).then((response) => {
        if (response.ok) {
          const result = response.json();
          result.then((body) => {
            resolve(body.token);
          });
        } else {
          reject(new AuthorizationError('Invalid login or password.'));
        }
      });
    });
  }

  /**
   * Sends request to the server for user registration.
   * @param {UserData} userData -instance of {@link UserData}.
   * @return {Promise<Error>} if user with this login is already registered then method reject returns
   * {@link AuthorizationError}. If user's is not registered, but his password is not valid method reject returns
   * {@link VerificationError}. If everything is alright method resolve contains redirection to {@link LoginPage}
   */
  register(userData) {
    return new Promise(((resolve, reject) => {
      fetch('/registration', {
        method: 'POST',
        body: userData,
      }).then((response) => {
        if (response.ok) {
          resolve();
        }
        const result = response.json();
        switch (response.status) {
          case 401: {
            result.then((errors) => {
              console.log(errors.error.message);
              reject(errors.error);
            });
            break;
          }
          case 422: {
            const serverErrors = [];
            result.then((errors) => {
              errors.errors.forEach((error) => {
                serverErrors.push(new VerificationError(error.field, error.message));
              });
              reject(serverErrors);
            });
            break;
          }
          case 500: {
            reject(new Error('Internal server error.'));
            break;
          }
        }
      });
    }));
  }

  /**
   * Sends request to the server for getting user items by its parent folder id.
   * @param {string} id - id of parent folder.
   * @return {Promise<[Object]>} - if everything is ok returns items list.
   */
  getItems(id) {
    return new Promise((resolve, reject) => {
      fetch(`/get-items/${id}`, {
        method: 'GET',
      }).then((response) => {
        const result = response.json();
        result.then((result) => {
          resolve(result.items);
        });
      });
    });
  }


  /**
   *  Sends request to the server for deleting item by its id.
   * @param {string} id - item id.
   * @param {string} type - item type. 'file' / 'folder'.
   * @return {Promise<number>} - status of server response if everything is ok and errors if something went wrong.
   */
  deleteItem(id, type) {
    if (type === 'folder') {
      return new Promise(((resolve, reject) => {
        fetch(`/delete-folder/${id}`, {
          method: 'DELETE',
        }).then((response) => {
          if (response.ok) {
            resolve();
          } else {
            const result = response.json();
            switch (response.status) {
              case 401: {
                result.then((error) => {
                  reject(new AuthorizationError(error.message));
                });
                break;
              }
              case 500: {
                reject(new Error('Internal server error.'));
                break;
              }
            }
          }
        });
      }));
    } else {
      return new Promise(((resolve, reject) => {
        fetch(`/delete-item/${id}`, {
          method: 'DELETE',
        }).then((response) => {
          if (response.ok) {
            resolve();
          } else {
            const result = response.json();
            switch (response.status) {
              case 401: {
                result.then((error) => {
                  reject(new AuthorizationError(error.message));
                });
                break;
              }
              case 500: {
                reject(new Error('Internal server error.'));
                break;
              }
            }
          }
        });
      }));
    }
  }

  /**
   * Sends request to server for uploading new file.
   * @param {string} parentId - id of parent folder where file will be loaded.
   * @param {File} file - file to be loaded.
   * @returns {Promise<>}
   */
  uploadFile(parentId, file) {
    return fetch(`/upload-item/${parentId}`, {
      method: 'POST',
      body: {
        file: file,
      },
    });
  }
}
