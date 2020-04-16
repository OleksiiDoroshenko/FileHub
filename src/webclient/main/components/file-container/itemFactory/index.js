import FolderItem from '../folder-item';
import FileItem from '../file-item';

/**
 * @typedef FileConfig.
 * @param {string} name - file name with type.
 * @param {string} mimeType - files mimeType.
 * @param {string} size - file size;
 */

/**
 * @typedef FolderConfig.
 * @param {string} name - folder name.
 * @param {string} itemsAmount - number of elements in the folder.
 */

/**
 * @typedef Item
 * @param type - item type file or folder.
 * @param config - FileConfig|FolderConfig.
 */

/**
 * Returns instance of {@link FileItem} or {@link FolderItem}.
 * @param {HTMLElement} container - container for element rendering.
 * @param {Item} item - configuration for render item.
 * @return {FileItem|FolderItem}
 */
export default function createItem(container, item) {
  switch (item.type) {
    case 'folder': {
      return new FolderItem(container, item.config);
    }
    case 'file': {
      item.config['fileIcon'] = _getFileIcon(item.config.mimeType);
      return new FileItem(container, item.config);
    }
  }
}

/**
 * Returns file icon by its mimeType.
 * @param {string} mimeType - files mimeType.
 * @return {string} bootstrap icon class.
 * @private
 */
function _getFileIcon(mimeType) {
  const icons = {
    image: 'glyphicon-picture',
    text: 'glyphicon-book',
    video: 'glyphicon-film',
    audio: 'glyphicon-music',
  };
  return icons[mimeType] ? icons[mimeType] : 'glyphicon-file';
}
