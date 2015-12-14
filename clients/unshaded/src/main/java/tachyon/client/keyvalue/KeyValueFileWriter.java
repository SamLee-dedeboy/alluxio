/*
 * Licensed to the University of California, Berkeley under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package tachyon.client.keyvalue;

import com.google.common.base.Preconditions;
import tachyon.TachyonURI;
import tachyon.client.file.FileOutStream;
import tachyon.client.file.TachyonFileSystem;
import tachyon.exception.TachyonException;

import java.io.IOException;

/**
 * Interface of the writer to create a Tachyon key-value file.
 */
public interface KeyValueFileWriter {

  class Factory {
    /**
     * Factory method to create a {@link KeyValueFileWriter} instance.
     *
     * @param uri Tachyon URI of the key-value file as output
     * @return an instance of a {@link KeyValueFileWriter}
     * @throws TachyonException if error occurs
     * @throws IOException if error occurs
     */
    public static KeyValueFileWriter create(TachyonURI uri) throws TachyonException, IOException {
      Preconditions.checkArgument(uri != null);
      TachyonFileSystem tfs = TachyonFileSystem.TachyonFileSystemFactory.get();
      FileOutStream fileOutStream = tfs.getOutStream(uri);
      return new KeyValueFileWriterImpl(fileOutStream);
    }
  }

  /**
   * Adds a key and the associated value to this writer.
   *
   * @param key key to put, cannot be null
   * @param value value to put, cannot be null
   */
  void put(byte[] key, byte[] value);

  /**
   * Completes and builds a KeyValue file.
   */
  void build();
}
