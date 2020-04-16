import UserData from '../../../models/user-data';
import AuthorizationError from '../../../models/errors/authorization-error';
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
    admin: 'Admin123456',
  };

  items = [
    {
      type: 'folder',
      config: {id: '0', parentId: '0', name: 'Documents', itemsAmount: '2'},
    },
    {
      type: 'folder',
      config: {id: '1', parentId: '0', name: 'Images', itemsAmount: '2'},
    },
    {
      type: 'folder',
      config: {id: '2', parentId: '0', name: 'Videos', itemsAmount: '1'},
    },
    {
      type: 'file',
      config: {id: '3', parentId: '0', name: 'test.txt', mimeType: 'text', size: '20KB'},
    },
  ];

  /**
   * Returns instance of {@link MockServer}.
   * <p> Instance has mocked HTTP requests.
   */
  constructor() {
    console.log('Current users');
    this.printUsers();

    fetchMock.config.overwriteRoutes = true;

    fetchMock
      .post('/login', ((url, request) => {
        const userData = new UserData(request.body.login, request.body.password);
        if (this.isUserRegistered(userData)) {
          return {
            status: 200,
            body: {token: `${userData.login}-token`},
          };
        } else {
          return 401;
        }
      }));

    fetchMock
      .post('/registration', ((url, request) => {
        const userData = new UserData(request.body.login, request.body.password);
        if (!this.isLoginRegistered(userData) && userData.password.length >= 10) {
          console.log(`New user ${userData.login} has been registered`);
          this.users[userData.login.toLowerCase()] = userData.password;
          return 200;
        } else {
          if (this.isLoginRegistered(userData)) {
            return {
              status: 401,
              body: {
                error: new AuthorizationError('User with this login already exists.'),
              },
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
      .get('express:/get-items/:id', ((url, request) => {
        const id = url.split('/')[2];
        let items = [];
        if (id === '0') {
          items = this.items;
        }
        return {items: items};
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
        let newFile = {type: 'file'};
        let config = {};

        config.id = this._getNextId();
        config.parentId = url.split('/')[2];
        config.name = file.name;
        config.mimeType = this._getMimeType(file.name);
        config.size = this._getFileSize(file.size);
        newFile.config = config;


        this.items.push(newFile);
        debugger;
        return 200;
      })));
  }

  /**
   * Prints all registered users.
   */
  printUsers() {
    for (const p in this.users) {
      console.log(p);
    }
  }

  /**
   * Checks if user with this login and password is registered.
   * @param {UserData} userData - instance of {@link UserData}.
   * @return {boolean} if user is registered returns true if it's not returns false.
   */
  isUserRegistered(userData) {
    const login = userData.login.toLowerCase();
    const password = userData.password;
    return this.users.hasOwnProperty(login) && this.users[login] === password;
  }

  /**
   * Checks if this login is registered.
   * @param {UserData} userData - instance of {@link UserData}.
   * @returns {boolean} if login is already registered returns True if not, false.
   */
  isLoginRegistered(userData) {
    const login = userData.login.toLowerCase();
    console.log(`login ${login} is ${this.users.hasOwnProperty(login)}`);
    return this.users.hasOwnProperty(login);
  }

  /**
   * Returns files mime type from its name.
   * @param fileName - file name with its type.
   * @returns {string} mime type.
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
   * @returns {number} - item id.
   * @private
   */
  _getNextId() {
    return +this.items[this.items.length - 1].config.id + 1;

  }

  /**
   * Converts file size to general view.
   * @param {number} size - file seze.
   * @returns {string} converted string.
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
