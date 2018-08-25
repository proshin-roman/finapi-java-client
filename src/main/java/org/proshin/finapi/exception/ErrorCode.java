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
package org.proshin.finapi.exception;

public enum ErrorCode {
    MISSING_FIELD,
    UNKNOWN_ENTITY,
    METHOD_NOT_ALLOWED,
    ENTITY_EXISTS,
    ILLEGAL_ENTITY_STATE,
    UNEXPECTED_ERROR,
    ILLEGAL_FIELD_VALUE,
    UNAUTHORIZED_ACCESS,
    BAD_REQUEST,
    UNSUPPORTED_ORDER,
    ILLEGAL_PAGE_SIZE,
    INVALID_FILTER_OPTIONS,
    TOO_MANY_IDS,
    BANK_SERVER_REJECTION,
    IBAN_ONLY_MONEY_TRANSFER_NOT_SUPPORTED,
    IBAN_ONLY_DIRECT_DEBIT_NOT_SUPPORTED,
    COLLECTIVE_MONEY_TRANSFER_NOT_SUPPORTED,
    INVALID_TWO_STEP_PROCEDURE,
    MISSING_TWO_STEP_PROCEDURE,
    UNSUPPORTED_MEDIA_TYPE,
    UNSUPPORTED_BANK,
    USER_NOT_VERIFIED,
    USER_ALREADY_VERIFIED,
    INVALID_TOKEN, LOCKED,
    NO_ACCOUNTS_FOR_TYPE_LIST,
    FORBIDDEN,
    NO_EXISTING_CHALLENGE,
    ADDITIONAL_AUTHENTICATION_REQUIRED,
    WEB_FORM_REQUIRED,
    WEB_FORM_ABORTED
}
