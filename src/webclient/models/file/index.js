/**
 * Value object that contains information about {@link FileItem}.
 */
export default class FileModel {
  _id;
  _parentId;
  _name;
  _size;
  _mimeType;
  TYPE = 'file';

  /**
   * Returns instance of {@link FileModel} with set parameters.
   * @param {string} id file identifier.
   * @param {string} parentId parent folder identifier.
   * @param {string} name file name.
   * @param {string} size file size.
   * @param {string} mimeType file mimeType.
   */
  constructor(id, parentId, name, size, mimeType) {
    this._id = id;
    this._parentId = parentId;
    this._name = name;
    this._size = size;
    this._mimeType = mimeType;
  }

  /**
   * Returns file identifier.
   * @return {string} file identifier.
   */
  get id() {
    return this._id;
  }

  /**
   * Returns file name.
   * @return {string} file name.
   */
  get name() {
    return this._name;
  }

  /**
   * Returns parent folder identifier.
   * @return {string} folder identifier.
   */
  get parentId() {
    return this._parentId;
  }

  /**
   * Returns file size.
   * @return {string} file size.
   */
  get size() {
    return this._size;
  }

  /**
   * Returns file mimeType.
   * @return {string} file mimeType.
   */
  get mimeType() {
    return this._mimeType;
  }

  /**
   * Returns 'file' constant.
   * @return {string} constant value.
   */
  get type() {
    return this.TYPE;
  }
}
