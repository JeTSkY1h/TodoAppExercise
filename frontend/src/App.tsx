import { Route, Routes } from 'react-router-dom';
import KanbanBoard from './components/KanbanBoard';
import Test from './components/Test';



function App() {

  return (
      <main>
        <Routes>
            <Route path='/' element={<KanbanBoard/>}/>
            <Route path='/t/:tagID' element={<KanbanBoard/>}/>
      </Routes>
      </main>
  );
}

export default App;