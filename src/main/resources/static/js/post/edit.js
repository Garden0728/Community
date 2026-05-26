const user = JSON.parse(localStorage.getItem('user') || 'null');
if (!user) { alert('로그인이 필요합니다.'); location.href = '/login'; }

const pageData = document.getElementById('pageData');
const postId = pageData.dataset.postId;
document.getElementById('category').value = pageData.dataset.postCategory;

async function submit() {
  const title = document.getElementById('title').value.trim();
  const content = document.getElementById('content').value.trim();
  if (!title || !content) { alert('제목과 내용을 모두 입력하세요.'); return; }
  const res = await fetch('/api/posts/' + postId, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ userId: user.userId, category: document.getElementById('category').value, title, content })
  });
  if (res.ok) {
    location.href = '/posts/' + postId;
  } else {
    alert('수정에 실패했습니다.');
  }
}
