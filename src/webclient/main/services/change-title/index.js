/**
 * Provide function fir changing site title.
 */
export default class TitleService {
  applicationName = 'FIleHub';

  /**
   * Changes site title.
   * @param {string} title - new title.
   */
  changeTitle(title) {
    document.title = `${title} - ${this.applicationName}`;
  }
}

