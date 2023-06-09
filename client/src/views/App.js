import AuthorizedApp from 'components/templates/AuthorizedApp/AuthorizedApp'
import UnauthorizedApp from 'components/templates/UnauthorizedApp/UnauthorizedApp'
import { useApi } from 'hooks/useApi'

function App() {
  const { user } = useApi()

  return true ? <AuthorizedApp /> : <UnauthorizedApp />
}

export default App
