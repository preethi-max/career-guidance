/**
 * Unified API Client for Student Career Guidance App
 * Wraps Fetch API, manages credentials & session cookie, handles JSON, and shows toasts
 */
const API = {
  baseUrl: '',

  async request(endpoint, options = {}) {
    const defaultHeaders = {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    };

    const config = {
      ...options,
      headers: {
        ...defaultHeaders,
        ...options.headers
      },
      credentials: 'same-origin' // preserves Spring Boot session cookie
    };

    if (config.body && typeof config.body === 'object') {
      config.body = JSON.stringify(config.body);
    }

    try {
      const response = await fetch(`${this.baseUrl}${endpoint}`, config);
      const isJson = response.headers.get('content-type')?.includes('application/json');
      const data = isJson ? await response.json() : await response.text();

      if (!response.ok) {
        if (response.status === 401) {
          // If not on login or public page, session expired
          const publicPages = ['/', '/index.html', '/login.html', '/register.html'];
          if (!publicPages.includes(window.location.pathname)) {
            sessionStorage.removeItem('CURRENT_USER');
            window.location.href = '/login.html?expired=true';
          }
        }
        const errorMsg = data.message || `Request failed with status ${response.status}`;
        throw new Error(errorMsg);
      }

      return data;
    } catch (err) {
      console.error(`API Error on [${endpoint}]:`, err);
      throw err;
    }
  },

  get(endpoint) {
    return this.request(endpoint, { method: 'GET' });
  },

  post(endpoint, body) {
    return this.request(endpoint, { method: 'POST', body });
  },

  put(endpoint, body) {
    return this.request(endpoint, { method: 'PUT', body });
  },

  delete(endpoint) {
    return this.request(endpoint, { method: 'DELETE' });
  },

  // Auth Helpers
  async getCurrentUser() {
    try {
      const res = await this.get('/api/auth/me');
      if (res && res.success && res.data) {
        sessionStorage.setItem('CURRENT_USER', JSON.stringify(res.data));
        return res.data;
      }
      sessionStorage.removeItem('CURRENT_USER');
      return null;
    } catch {
      sessionStorage.removeItem('CURRENT_USER');
      return null;
    }
  },

  async logout() {
    try {
      await this.post('/api/auth/logout', {});
    } catch (e) {
      console.warn("Logout warning:", e);
    } finally {
      sessionStorage.removeItem('CURRENT_USER');
      window.location.href = '/login.html';
    }
  },

  async requireAuth(allowedRoles = []) {
    const user = await this.getCurrentUser();
    if (!user) {
      window.location.href = `/login.html?redirect=${encodeURIComponent(window.location.pathname)}`;
      return null;
    }

    if (allowedRoles.length > 0 && !allowedRoles.includes(user.role)) {
      if (user.role === 'ADMIN') {
        window.location.href = '/admin-dashboard.html';
      } else if (user.role === 'CAREER_GUIDE') {
        window.location.href = '/guide-dashboard.html';
      } else {
        window.location.href = '/dashboard.html';
      }
      return null;
    }

    return user;
  },

  // Toast notifications
  showToast(message, type = 'info') {
    let container = document.getElementById('toast-container');
    if (!container) {
      container = document.createElement('div');
      container.id = 'toast-container';
      document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.innerHTML = `
      <span>${message}</span>
      <button style="background:none; border:none; color:inherit; cursor:pointer; font-size:1.1rem; margin-left:auto;" onclick="this.parentElement.remove()">×</button>
    `;

    container.appendChild(toast);
    setTimeout(() => {
      toast.style.opacity = '0';
      toast.style.transform = 'translateX(100%)';
      toast.style.transition = 'all 0.3s ease';
      setTimeout(() => toast.remove(), 300);
    }, 4000);
  }
};
