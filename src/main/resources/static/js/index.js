const params = new URLSearchParams(location.search);
const cat = params.get('category') || '';
document.querySelectorAll('#categoryTabs .nav-link').forEach(a => {
  if (a.dataset.cat === cat) a.classList.add('active');
});

function goCreate() {
  const user = JSON.parse(localStorage.getItem('user') || 'null');
  if (!user) { alert('로그인이 필요합니다.'); location.href = '/login'; return; }
  location.href = '/posts/create';
}
