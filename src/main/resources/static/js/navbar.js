/**
 * Unified Navbar and Navigation Manager
 */
document.addEventListener('DOMContentLoaded', async () => {
  renderNavbar();
});

async function renderNavbar() {
  const header = document.querySelector('.app-header');
  if (!header) return;

  const user = await API.getCurrentUser();
  const currentPath = window.location.pathname;

  let navLinksHtml = `
    <li><a href="/index.html" class="nav-link ${currentPath === '/' || currentPath === '/index.html' ? 'active' : ''}">Home</a></li>
    <li><a href="/careers.html" class="nav-link ${currentPath === '/careers.html' ? 'active' : ''}">Careers</a></li>
    <li><a href="/courses.html" class="nav-link ${currentPath === '/courses.html' ? 'active' : ''}">Courses</a></li>
    <li><a href="/mentors.html" class="nav-link ${currentPath === '/mentors.html' ? 'active' : ''}">Mentors</a></li>
  `;

  if (user && user.role === 'STUDENT') {
    navLinksHtml += `
      <li><a href="/dashboard.html" class="nav-link ${currentPath === '/dashboard.html' ? 'active' : ''}">Dashboard</a></li>
      <li><a href="/recommendations.html" class="nav-link ${currentPath === '/recommendations.html' ? 'active' : ''}">Recommendations</a></li>
      <li><a href="/progress.html" class="nav-link ${currentPath === '/progress.html' ? 'active' : ''}">My Progress</a></li>
    `;
  } else if (user && user.role === 'CAREER_GUIDE') {
    navLinksHtml += `
      <li><a href="/guide-dashboard.html" class="nav-link ${currentPath === '/guide-dashboard.html' ? 'active' : ''}">Guide Portal</a></li>
    `;
  } else if (user && user.role === 'ADMIN') {
    navLinksHtml += `
      <li><a href="/admin-dashboard.html" class="nav-link ${currentPath === '/admin-dashboard.html' ? 'active' : ''}">Admin Center</a></li>
    `;
  }

  let authSectionHtml = '';
  if (user) {
    let dashboardUrl = '/dashboard.html';
    if (user.role === 'CAREER_GUIDE') dashboardUrl = '/guide-dashboard.html';
    if (user.role === 'ADMIN') dashboardUrl = '/admin-dashboard.html';

    authSectionHtml = `
      <div style="display:flex; align-items:center; gap:0.75rem;">
        <a href="${dashboardUrl}" class="btn btn-sm btn-secondary" style="display:flex; align-items:center; gap:0.4rem;">
          <span style="display:inline-block; width:8px; height:8px; border-radius:50%; background-color:#10b981;"></span>
          ${user.fullName.split(' ')[0]}
          <span class="badge ${user.role === 'ADMIN' ? 'badge-danger' : (user.role === 'CAREER_GUIDE' ? 'badge-warning' : 'badge-blue')}" style="font-size:0.65rem; padding:0.15rem 0.4rem;">${user.role}</span>
        </a>
        <button onclick="API.logout()" class="btn btn-sm btn-primary">Logout</button>
      </div>
    `;
  } else {
    authSectionHtml = `
      <a href="/login.html" class="btn btn-sm btn-secondary">Log In</a>
      <a href="/register.html" class="btn btn-sm btn-accent">Get Started</a>
    `;
  }

  header.innerHTML = `
    <div class="container navbar">
      <a href="/index.html" class="brand-logo">
        <div class="brand-icon">
          <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M22 10v6M2 10l10-5 10 5-10 5z"/>
            <path d="M6 12v5c3 3 9 3 12 0v-5"/>
          </svg>
        </div>
        <span>CareerCompass</span>
      </a>

      <ul class="nav-links">
        ${navLinksHtml}
      </ul>

      <div class="nav-auth">
        ${authSectionHtml}
        <button class="menu-toggle" id="menuToggleBtn" aria-label="Toggle navigation">
          ☰
        </button>
      </div>
    </div>
  `;

  // Attach mobile menu toggle
  const toggleBtn = document.getElementById('menuToggleBtn');
  if (toggleBtn) {
    toggleBtn.addEventListener('click', () => {
      const nav = document.querySelector('.nav-links');
      if (nav.style.display === 'flex') {
        nav.style.display = 'none';
      } else {
        nav.style.display = 'flex';
        nav.style.flexDirection = 'column';
        nav.style.position = 'absolute';
        nav.style.top = '70px';
        nav.style.left = '0';
        nav.style.right = '0';
        nav.style.background = '#ffffff';
        nav.style.padding = '1.5rem';
        nav.style.boxShadow = '0 10px 15px -3px rgba(15,23,42,0.1)';
      }
    });
  }
}
