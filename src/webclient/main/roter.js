/**
 *Implements page changing by changing url hash.
 */
export default class Router {
  constructor(container, pageMapping) {
    this.container = container;
    this.pageMapping = pageMapping;
    this.init();
  }

  /**
   *Adds event listener for hash changing process.
   */
  init() {
    window.addEventListener('hashchange', () => {
      const hash = window.location.hash.slice(1);
      this.renderPage(hash);
    });
  }

  /**
   *Renders page from map with all possible url hashes.
   * <p> If such hash isn't found, calls another method.
   * @param{string} hash url hash("#/...")
   */
  renderPage(hash) {
    this.container.innerHTML = '';
    if (this.pageMapping[hash]) {
      const Page = this.pageMapping[hash];
      new Page(this.container, {});
    } else {
      this.defaultPage();
    }
  }

  /**
   *If url hash equals '#/' renders default page from pages map with key 'default'.
   * If url hash equals something else that not contains in pages map renders error 404 page.
   */
  defaultPage() {
    this.container.innerHTML = '';
    if (window.location.hash === '#/') {
      window.location.hash = `#${this.pageMapping['default']}`;
    } else {
      const Page = this.pageMapping['error'];
      new Page(this.container, {});
    }
  }
}
