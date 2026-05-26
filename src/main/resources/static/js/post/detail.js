const user = JSON.parse(localStorage.getItem('user') || 'null');
const pageData = document.getElementById('pageData');
const postId = parseInt(pageData.dataset.postId);
const postUserId = parseInt(pageData.dataset.postUserId);

if (user && user.userId === postUserId) {
  document.querySelector('.post-actions').style.display = 'flex';
}
if (!user) document.getElementById('commentForm').style.display = 'none';

if (user) {
  document.querySelectorAll('.comment-actions').forEach(el => {
    if (parseInt(el.dataset.userId) === user.userId) el.style.display = 'flex';
  });
}

async function deletePost(id) {
  if (!confirm('삭제하시겠습니까?')) return;
  await fetch('/api/posts/' + id + '?userId=' + user.userId, { method: 'DELETE' });
  location.href = '/';
}

async function submitComment(postId) {
  const content = document.getElementById('commentContent').value.trim();
  if (!content) { alert('댓글 내용을 입력하세요.'); return; }
  await fetch('/api/comments/create', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ content, userId: user.userId, postId })
  });
  location.reload();
}

function showEditForm(id) {
  document.querySelector('#comment-' + id + ' .comment-view').style.display = 'none';
  document.getElementById('edit-' + id).style.display = 'block';
}

function hideEditForm(id) {
  document.querySelector('#comment-' + id + ' .comment-view').style.display = 'block';
  document.getElementById('edit-' + id).style.display = 'none';
}

async function submitEdit(id) {
  const content = document.getElementById('edit-content-' + id).value.trim();
  if (!content) { alert('내용을 입력하세요.'); return; }
  await fetch('/api/comments/' + id, {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ userId: user.userId, content })
  });
  location.reload();
}

async function deleteComment(id) {
  if (!confirm('댓글을 삭제하시겠습니까?')) return;
  await fetch('/api/comments/' + id + '?userId=' + user.userId, { method: 'DELETE' });
  location.reload();
}
