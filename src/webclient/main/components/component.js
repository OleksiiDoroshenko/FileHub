/**
 * Base component for html elements manipulation.
 */
export default class Component {
  constructor(container, componentConfig) {
    this.container = container;
    Object.assign(this, componentConfig);
    this.render();
    this.initInnerComponents();
  }

  /**
   * Renders inner html representation into another html element(container).
   */
  render() {
    const div = document.createElement('div');
    div.innerHTML = this.markup().trim();
    this.container.appendChild(div.firstChild);
  }

  /**
   * Returns html representation of element or group of elements.
   */
  markup() {
  }

  /**
   * Adds event listener to container or inner html elements;
   */
  addEventListener() {
  }

  /**
   * Renders components into container.
   */
  initInnerComponents() {
  }
}
