/**
 * Base component for html elements manipulation.
 */
export default class Component {
  /**
   * @param {HTMLElement} container - container for element rendering.
   * @param {Object} componentConfig - initial configuration for current html element.
   */
  constructor(container, componentConfig) {
    this.container = container;
    Object.assign(this, componentConfig);
    this._render();
    this._initInnerComponents();
  }

  /**
   * Renders inner html representation into another html element(container).
   */
  _render() {
    const div = document.createElement('div');
    div.innerHTML = this._markup().trim();
    this.container.appendChild(div.firstChild);
  }

  /**
   * Returns html representation of element or group of elements.
   */
  _markup() {
  }

  /**
   * Adds event listener to container or inner html elements.
   */
  addEventListener() {
  }

  /**
   * Renders components into container.
   */
  _initInnerComponents() {
  }
}
