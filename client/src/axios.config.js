import axios from 'axios'

const AxiosApi = axios.create({
  baseURL: 'http://localhost:8080',
})

AxiosApi.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')

    if (token) {
      config.headers.authorization = `Bearer ${token}`
    }

    // Dodaj nagłówki CORS
    config.headers['Access-Control-Allow-Origin'] = '*' // Ustaw żądania z dowolnego źródła
    config.headers['Access-Control-Allow-Methods'] = 'GET, POST, PUT, DELETE' // Ustaw dozwolone metody żądań

    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

export default AxiosApi
