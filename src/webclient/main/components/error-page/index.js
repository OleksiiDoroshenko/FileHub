import Component from '../component.js';

/**
 * Implements html page that alerts user that page not found.
 */
export default class ErrorPage extends Component {
  /**
   * @inheritdoc
   */
  _markup() {
    return `
            <section class="container base-form login-form error" data-render="error-page">
                 <header class="header">
                    <img class="logo" alt="logo" src="./static/images/teamdev.png" width="150">
                    <i class="glyphicon glyphicon-user user-icon"></i>
                    <h1>Error 404. Page not found.</h1>
                </header>
            </section>
        `;
  }
}
