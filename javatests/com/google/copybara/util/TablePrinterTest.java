/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.copybara.util;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

import com.google.common.base.Splitter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TablePrinterTest {

  @Test
  public void testPrint() {
    TablePrinter underTest = new TablePrinter("One", "Two", "Three", "Four");
    underTest.addRow(15, true, "Just a \nTest", null);
    String table = underTest.print();
    assertThat(Splitter.on('\n').split(table.trim())).containsExactly(
        "+----+-----+------------+-----+",
        "|One |Two  |Three       |Four |",
        "+----+-----+------------+-----+",
        "|15  |true |Just a Test |null |",
        "+----+-----+------------+-----+");
  }

  @Test
  public void testMissingCol() {
    TablePrinter underTest = new TablePrinter("One", "Two", "Three", "Four");
    try {
      underTest.addRow(15, true, "Just a Test");
      fail("Excected an Exception");
    } catch (IllegalArgumentException expected) {
      assertThat(expected).hasMessageThat()
          .contains("Wrong number of values in row; expected 4. Got: 3");
    }
  }
}
