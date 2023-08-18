import { baseURL, setContentTypeJsonHeader, setContentTypeMultiPartFormDataHeader } from './fetchConfig';

export function useGet(url: string, token: String) {
  return useFetch(`${baseURL}${url}`, {
    key: url,
    method: 'GET',
    baseURL: baseURL,
    onRequest: (context) => {
      context.options.headers = new Headers(context.options.headers);
      context.options.headers.append('Authorization', `Bearer ${token}`);
    },
    onRequestError({ request, options, error }) {
      // if
    },
    onResponse({ request, response, options }) {
      // Process the response data
      // localStorage.setItem('token', response._data.token)
    },
    onResponseError({ request, response, options }) {
      // Handle the response errors
    }
  });
}

// export function useLogin(url: string) {
//   return useFetch(`${baseURL}${url}`, {
//     key: url,
//     method: 'POST',
//     baseURL: baseURL,
//     onRequest: (context) => {
//       context.options.headers = new Headers(context.options.headers);
//       context.options.headers = setContentTypeJsonHeader(context.options.headers);
//     },
//   });
// }

export function useLogin(url: string, data: any) {
  return useFetch(`${baseURL}${url}`, {
    key: url,
    method: 'POST',
    baseURL: baseURL,
    body: JSON.stringify(data),
    onRequest: (context) => {
      context.options.headers = new Headers(context.options.headers);
      context.options.headers = setContentTypeJsonHeader(context.options.headers);
    }
  });
}


export function useSignUp(url: string) {
  return useFetch(`${baseURL}${url}`, {
    key: url,
    method: 'POST',
    baseURL: baseURL,
    onRequest: (context) => {
      context.options.headers = new Headers(context.options.headers);
      context.options.headers = setContentTypeMultiPartFormDataHeader(context.options.headers);
    },
  });
}