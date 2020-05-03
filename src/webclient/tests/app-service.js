import AppService from '../main/services/app-secrvice';
import UserData from '../models/user-data';
import AuthorizationError from '../models/errors/authorization-error';

const {module, test} = QUnit;
const service = new AppService(true);

export default module('App service test', function(hook) {
  /*test('Register method should returns exception when password is invalid.', async (assert) => {
    assert.expect(1);
    const userData = new UserData('Alex', 'maksdlo21');
    service.register(userData)
      .catch((errors) => {
        let message = errors.errors[0].message;
        assert.strictEqual(message, 'Password should be longer than 10 characters.',
          'Should throw Verification error');
      });
  });*/

  /*test('Register method should register user if data is valid.', async (assert) => {
    assert.expect(1);
    const userData = new UserData('Alex', 'Mdaskjdsdasa1543');
    service.register(userData)
      .then(() => {
        assert.ok('ok', 'Register method should register user if data is valid.');
      });
  });*/

  test('Register method should return exception if user with this login already registered.', async (assert) => {
    assert.expect(1);
    const userData = new UserData('Alex1', 'Mdaskjdsdasa1543');
    service.register(userData).then(() => {
      try{
      service.register(userData).catch(error=>{
        assert.ok(true);
      })}catch (e) {
        console.log(e);
      }
    });
  });

  /*test('Login method should return exception if user with this login is not registered.', async (assert) => {
    assert.expect(1);
    const userData = new UserData('Vas9', 'Mdaskjdsdasa1543');
    service.logIn(userData).catch((error) => {
      assert.ok(error instanceof AuthorizationError, 'Should throw AuthorizationError.');
    });
  });

  test('Login method should redirect user to the fileHub page if everything is ok.', async (assert) => {
    assert.expect(1);
    const userData = new UserData('admin', 'Admin123456');
    service.logIn(userData)
      .then((message) => {
        assert.ok(`${userData.login}-token`, 'Should register and redirect user.');
      });
  });*/
});
