import UserData from '../../../models/user-data';
import VerificationError from '../../../models/errors/verification-error';
import fetchMock from '../../../../../node_modules/fetch-mock/esm/client.js';

/**
 * Pretending to be a server.
 * <p> Can answer to some of requests.
 */
export default class MockServer {
  /**
   * @typedef User
   *  @param {string} login - users login.
   *  @param {string} password - users password.
   */

  /**
   * Registered users.
   * @type {{User}}
   */
  users = {
    Admin: 'Admin123456',
  };

  /**
   * List of user items.
   * @type {[ListItem]}
   */
  items = [
    {
      id: '1', parentId: '0', name: 'Documents', itemsAmount: '2', type: 'folder',
    },
    {
      id: '2', parentId: '0', name: 'Images', itemsAmount: '2', type: 'folder',
    },
    {
      id: '3', parentId: '0', name: 'Videos', itemsAmount: '1', type: 'folder',
    },
    {
      id: '4', parentId: '0', name: 'test.txt', mimeType: 'text', size: '20KB', type: 'file',
    },
  ];

  /**
   * Returns instance of {@link MockServer}.
   * <p> Instance has mocked HTTP requests.
   */
  constructor() {
    fetchMock.config.overwriteRoutes = true;

    fetchMock
      .post('/login', ((url, request) => {
        const userData = new UserData(request.body.login, request.body.password);
        if (this.isUserRegistered(userData)) {
          return {
            status: 200,
            body: {token: `${userData.login}-token`, rootId: '0'},
          };
        } else {
          return {
            status: 401,
            body: 'Invalid login or password.',
          };
        }
      }));

    fetchMock
      .post('/register', ((url, request) => {
        const userData = new UserData(request.body.login, request.body.password);
        if (!this.isLoginRegistered(userData) && userData.password.length >= 10) {
          this.users[userData.login] = userData.password;
          return 200;
        } else {
          if (this.isLoginRegistered(userData)) {
            return {
              status: 401,
              body: 'User with this login already exists.',
            };
          } else {
            const errors = [];
            errors.push(new VerificationError('password', 'Password should be longer than 10 characters.'));
            return {
              status: 422,
              body: {
                errors,
              },
            };
          }
        }
      }));

    fetchMock
      .get('express:/folder/:id/content', ((url) => {
        const id = url.split('/')[2];
        if (id === '0') {
          return {items: this.items};
        }
        return {
          status: 404,
          body: 'Folder not found.',
        };
      }), 2000);

    fetchMock
      .get('/folder/root', ((url, request) => {
        const token = request.headers.token;
        if (token === 'Admin-token') {
          return {
            status: 200,
            body: {
              folder: {id: '0', parentId: '', name: 'Root', itemsAmount: '4', type: 'folder'},
            },
          };
        }
        return {
          status: 404,
          body: 'Folder not found.',
        };
      }));

    fetchMock
      .delete('express:/delete-folder/:id', ((url) => {
        const id = url.split('/')[2];
        this.items = Object.entries(this.items).reduce((acc, [index, item]) => {
          if (item.config.id !== id) {
            acc.push(item);
          }
          return acc;
        }, []);
        return 200;
      }));

    fetchMock
      .delete('express:/delete-item/:id', ((url) => {
        const id = url.split('/')[2];
        this.items = Object.entries(this.items).reduce((acc, [index, item]) => {
          if (item.config.id !== id) {
            acc.push(item);
          }
          return acc;
        }, []);
        return 200;
      }));

    fetchMock
      .post('express:/upload-item/:id', (((url, request) => {
        const file = request.body.file;
        const newFile = {type: 'file'};
        const config = {};

        config.id = this._getNextId();
        config.parentId = url.split('/')[2];
        config.name = file.name;
        config.mimeType = this._getMimeType(file.name);
        config.size = this._getFileSize(file.size);
        newFile.config = config;


        this.items.push(newFile);

        return 200;
      })));
  }

  /**
   * Checks if user with this login and password is registered.
   * @param {UserData} userData - instance of {@link UserData}.
   * @return {boolean} if user is registered returns true if it's not returns false.
   */
  isUserRegistered(userData) {
    const login = userData.login;
    const password = userData.password;
    return this.users.hasOwnProperty(login) && this.users[login] === password;
  }

  /**
   * Checks if this login is registered.
   * @param {UserData} userData - instance of {@link UserData}.
   * @return {boolean} if login is already registered returns True if not, false.
   */
  isLoginRegistered(userData) {
    const login = userData.login;
    return this.users.hasOwnProperty(login);
  }

  /**
   * Returns files mime type from its name.
   * @param {string} fileName - file name with its type.
   * @return {string} mime type.
   * @private
   */
  _getMimeType(fileName) {
    const type = fileName.split('.')[1];
    const types = {
      text: ['txt', 'doc', 'docx'],
      video: ['mpeg', 'mp4'],
      audio: ['mp3', 'aac'],
      image: ['gif', 'jpeg', 'png', 'jpg'],
    };
    return Object.entries(types).reduce((acc, [key, value]) => {
      if (value.includes(type)) {
        acc = key;
      }
      return acc;
    }, '');
  }

  /**
   * Returns available id for list item.
   * @return {number} - item id.
   * @private
   */
  _getNextId() {
    return +this.items[this.items.length - 1].config.id + 1;
  }

  /**
   * Converts file size to general view.
   * @param {number} size - file seze.
   * @return {string} converted string.
   * @private
   */
  _getFileSize(size) {
    if (size === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    const i = Math.floor(Math.log(size) / Math.log(k));
    return parseFloat((size / Math.pow(k, i)).toFixed(0)) + sizes[i];
  }
}
