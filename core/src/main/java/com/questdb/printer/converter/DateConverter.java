/*******************************************************************************
 *    ___                  _   ____  ____
 *   / _ \ _   _  ___  ___| |_|  _ \| __ )
 *  | | | | | | |/ _ \/ __| __| | | |  _ \
 *  | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *   \__\_\\__,_|\___||___/\__|____/|____/
 *
 * Copyright (C) 2014-2017 Appsicle
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package com.questdb.printer.converter;

import com.questdb.std.time.Dates;
import com.questdb.misc.Unsafe;
import com.questdb.printer.JournalPrinter;
import com.questdb.txt.sink.StringSink;

public class DateConverter extends AbstractConverter {
    private final StringSink sink = new StringSink();

    public DateConverter(JournalPrinter printer) {
        super(printer);
    }

    @Override
    public void convert(StringBuilder stringBuilder, JournalPrinter.Field field, Object obj) {
        final long millis = Unsafe.getUnsafe().getLong(obj, field.getOffset());
        if (millis == 0) {
            stringBuilder.append(getPrinter().getNullString());
        } else {
            Dates.appendDateTime(sink, millis);
            stringBuilder.append(sink);
            sink.clear();
        }
    }
}
