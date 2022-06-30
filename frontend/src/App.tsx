import { Route, Routes } from 'react-router-dom';
import KanbanBoard from './components/KanbanBoard';
import LoginPage from './components/LoginPage';
import RegisterPage from './components/RegisterPage';

function App() {

  return (
      <main>
        <Routes>
            <Route path='/' element={<LoginPage/>}/>
            <Route path='/register' element={<RegisterPage/>}/>
            <Route path='/kanban' element={<KanbanBoard/>}/>
            <Route path='/:tagID' element={<KanbanBoard/>}/>
      </Routes>
      </main>
  );
}

export default App;