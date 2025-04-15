import { AuthInterceptor } from './auth-interceptor';
import { AuthService } from './auth.service';

describe('AuthInterceptor', () => {
  it('should create an instance', () => {
    const mockAuthService = {
      getToken: () => 'fake-token'
    } as AuthService;

    const interceptor = new AuthInterceptor(mockAuthService);
    expect(interceptor).toBeTruthy();
  });
});