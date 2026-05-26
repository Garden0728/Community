const user = JSON.parse(localStorage.getItem('user') || 'null');
if (!user) { alert('로그인이 필요합니다.'); location.href = '/login'; }

async function loadProfile() {
  const res = await fetch('/api/users/' + user.userId);
  const data = await res.json();
  document.getElementById('loginId').value = data.loginId;
  document.getElementById('name').value = data.name;
  document.getElementById('nickname').value = data.nickname;
  document.getElementById('phone').value = data.phone;
  document.getElementById('gender').value = data.gender === 'MALE' ? '남자' : '여자';
  document.getElementById('createdAt').value = data.createdAt;
}

async function updateProfile() {
  const body = {
    name: document.getElementById('name').value,
    nickname: document.getElementById('nickname').value,
    phone: document.getElementById('phone').value
  };
  const res = await fetch('/api/users/' + user.userId, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body)
  });
  const msg = document.getElementById('updateMsg');
  if (res.ok) {
    const data = await res.json();
    localStorage.setItem('user', JSON.stringify({ userId: data.id, loginId: data.loginId, nickname: data.nickname }));
    showMsg(msg, 'success', '정보가 수정되었습니다.');
  } else {
    const data = await res.json();
    showMsg(msg, 'danger', data.message || '수정에 실패했습니다.');
  }
}

async function deleteAccount() {
  if (!confirm('정말 탈퇴하시겠습니까?')) return;
  await fetch('/api/users/' + user.userId, { method: 'DELETE' });
  localStorage.removeItem('user');
  location.href = '/';
}

async function loadMyPosts() {
  const res = await fetch('/api/posts/user/' + user.userId);
  const posts = await res.json();
  const list = document.getElementById('myPostList');
  if (!posts.length) {
    list.innerHTML = '<div class="list-group-item text-muted text-center py-4">작성한 게시글이 없습니다.</div>';
    return;
  }
  list.innerHTML = posts.map(p =>
    `<a href="/posts/${p.id}" class="list-group-item list-group-item-action py-3 px-4">
      <div class="d-flex justify-content-between align-items-center">
        <div>
          <span class="badge bg-secondary me-2">${p.categoryName}</span>
          <span class="fw-semibold">${p.title}</span>
        </div>
        <small class="text-muted text-nowrap ms-3">${p.createdAt}</small>
      </div>
    </a>`
  ).join('');
}

function showMsg(el, type, text) {
  el.className = 'alert alert-' + type;
  el.textContent = text;
}

document.querySelector('a[href="#tabPosts"]').addEventListener('shown.bs.tab', loadMyPosts);
loadProfile();
