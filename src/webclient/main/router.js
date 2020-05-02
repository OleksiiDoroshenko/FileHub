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
    const staticPart = `/${hash.split('/')[1]}`;
    if (staticPart.length > 1 && this._getUrlTemplate(staticPart)) {
      const urlTemplate = this._getUrlTemplate(staticPart);
      this._createPage(hash, urlTemplate);
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

  /**
   * Returns object with all dynamic parameters that hash contains.
   * @param {string} url - url.
   * @param {string} urlTemplate - url template that contains that describes rules for url parsing.
   * @returns {{Object}} - contains dynamic url parameters.
   * @private
   */
  _getDynamicPart(url, urlTemplate) {
    const dynamicParts = urlTemplate.split('/');
    const keyIndexMap = dynamicParts.reduce((acc, value, index) => {
      if (value.startsWith(':')) {
        const key = value.slice(1);
        acc[key] = index;
      }
      return acc;
    }, {});

    const urlParts = url.split('/');
    return Object.entries(keyIndexMap).reduce((acc, [key, index]) => {
      acc[key] = urlParts[index];
      return acc;
    }, {});
  }

  /**
   * Creates page from pageMapping field with all necessary parameters.
   * @param {string} url - url.
   * @param {string} urlTemplate - url template that contains that describes rules for url parsing.
   * @private
   */
  _createPage(url, urlTemplate) {
    const dynamicParams = this._getDynamicPart(url, urlTemplate);
    const page = this.pageMapping[urlTemplate];
    page(dynamicParams);
  }

  /**
   * Returns url template from pageMapping field.
   * @param {string} staticPart - url part that contains unchangeable part.
   * @returns {string} url template.
   * @private
   */
  _getUrlTemplate(staticPart) {
    return Object.keys(this.pageMapping).find((urlTemplate) => {
      if (urlTemplate.startsWith(staticPart)) {
        return true;
      }
    });
  }
}
