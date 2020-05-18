import Component from '../../component.js';

/**
 * Renders icon that processes actions such as 'delete', 'upload', 'download'.
 */
export default class ActionIcon extends Component {
  /**
   * @inheritdoc
   */
  constructor(container, componentConfig) {
    super(container, componentConfig);
    this.addIconClass(this.type);
  }

  /**
   * @inheritdoc
   * @private
   */
  _markup() {
    return ` <i class="glyphicon"></i>`;
  }

  /**
   * Adds bootstrap class for current icon.
   * @param {string} type - icon type.
   */
  addIconClass(type) {
    const iconClass = this.getClassByType(type);
    this.container.lastChild.classList.add(iconClass);
  }

  /**
   * Returns bootstrap class by icon type.
   * @param {string} type - icon type 'download', 'delete', 'upload'.
   * @return {string} returns bootstrap icon class.
   */
  getClassByType(type) {
    switch (type) {
      case 'download': {
        return 'glyphicon-download';
      }
      case 'delete': {
        return 'glyphicon-remove-circle';
      }
      case 'upload': {
        return 'glyphicon-upload';
      }
    }
    return '';
  }

  /**
   * @inheritdoc
   */
  addEventListener(event, handler) {
    this.container.lastChild.addEventListener(event, handler);
  }
}
