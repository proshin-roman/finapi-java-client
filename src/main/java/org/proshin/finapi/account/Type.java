/*
 * Copyright 2018 Roman Proshin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.proshin.finapi.account;

/**
 * @todo #21 This enum can be refactored to a general class - think about it
 */
public enum Type {

    Checking(1),

    Savings(2),

    CreditCard(3),

    Security(4),

    Loan(5),

    Pocket(6),

    Membership(7),

    Bausparen(8);

    private final int code;

    Type(final int code) {
        this.code = code;
    }

    public int asCode() {
        return this.code;
    }

    public static class TypeOf {
        private final int code;

        public TypeOf(final int code) {
            this.code = code;
        }

        public Type get() {
            for (Type type : Type.values()) {
                if (type.asCode() == this.code) {
                    return type;
                }
            }
            throw new IllegalArgumentException(String.format("Unknown type %d", this.code));
        }
    }
}
