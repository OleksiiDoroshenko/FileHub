/**
 * Provide function fir changing site title.
 */
export default class TitleService {
  applicationName = 'FileHub';

  /**
   * Changes site title.
   * @param {string} title - new title.
   */
  changeTitle(title) {
    document.title = `${title} - ${this.applicationName}`;
  }
}

