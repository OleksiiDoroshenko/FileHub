/**
 * Implements page changing by changing url hash.
 */
export default class Router {
  /**
   * Class constructor.
   * @param {HTMLElement} container - root container for page rendering.
   * @param {Window} window - window element.
   * @param {Object} pageMapping - map of possible links and pages for rendering.
   */
  constructor(container, window, pageMapping) {
    this.container = container;
    this.pageMapping = pageMapping;
    this.window = window;
    this._init();
    this._hashchangeHandler();
  }

  /**
   * Adds event listener for hash changing process.
   */
  _init() {
    this.window.addEventListener('hashchange', () => {
      this._hashchangeHandler();
    });
  }

  _hashchangeHandler() {
    const hash = this.window.location.hash.slice(1);
    this._renderPage(hash);
  }

  /**
   * Renders page from map with all possible url hashes.
   * <p> If such hash isn't found, calls another method.
   * @param {string} hash - url hash("#/...")
   */
  _renderPage(hash) {
    this.container.innerHTML = '';
    if (this.pageMapping[hash]) {
      this.pageMapping[hash]();
    } else {
      this._renderDefaultOrErrorPage(hash);
    }
  }

  /**
   * If url hash equals '#/' renders default page from pages map with key 'default'.
   * If url hash equals something else that not contains in pages map renders error 404 page.
   * @param {string} hash - string type of '#/...'.
   */
  _renderDefaultOrErrorPage(hash) {
    this.container.innerHTML = '';
    if (hash === '/' || hash === '') {
      this.window.location.hash = `#${this.pageMapping['default']}`;
      this._hashchangeHandler();
    } else {
      this.pageMapping['error']();
    }
  }
}
