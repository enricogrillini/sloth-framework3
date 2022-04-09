package it.eg.sloth.core.base;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project: sloth3-framework
 * Copyright (C) 2022-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
class ObjectUtilTest {

    @Test
    void coalesceTest() {
        assertEquals("Prova", ObjectUtil.coalesce("", null, "Prova", "Pippo"));
        assertEquals(null, ObjectUtil.coalesce("", null));
        assertEquals(null, ObjectUtil.coalesce());
        assertEquals(Double.valueOf(5), ObjectUtil.coalesce("", null, Double.valueOf(5)));
    }

    @Test
    void isNullTest() {
        assertTrue(ObjectUtil.isNull(null));
        assertTrue(ObjectUtil.isNull(""));
        assertFalse(ObjectUtil.isNull(Double.valueOf(0)));
    }
}
