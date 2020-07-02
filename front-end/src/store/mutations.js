export default {
  // `/api/me` API 요청 결과를 사용하여 사용자 정보 업데이트
  updateMyData (state, data) {
    state.user.name = data.user.name
    state.teams = data.teams
    state.boards = data.boards
  },
  // 새로 생성된 팀 추가
  addTeam (state, team) {
    state.teams.push(team)
  },
  // 새로 생성된 보드 추가
  addBoard (state, board) {
    state.boards.push(board)
  }
}
