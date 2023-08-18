// fetchConfig.js
import {useAuthStore} from "@/stores/auth";

export const baseURL = 'http://localhost:8000';

export function setContentTypeJsonHeader(headers: Headers) {
    headers.append('Content-Type', 'application/json');
    return headers;
}

export function setContentTypeMultiPartFormDataHeader(headers: Headers) {
    headers.append('Content-Type', 'multipart/form-data');
    return headers;
}