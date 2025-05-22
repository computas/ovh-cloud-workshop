import React, { useEffect, useState } from 'react';
import computasLogo from './assets/computas-logo.svg';

// Define the Todo type
interface Todo {
  id: number;
  title: string;
  completed: boolean;
}

const API_URL = 'http://localhost:8080/api/todos'; // Example public API

const TodoApp: React.FC = () => {
  const [todos, setTodos] = useState<Todo[]>([]);
  const [newTodo, setNewTodo] = useState('');
  const [loading, setLoading] = useState(false);

  // Fetch todos on mount
  useEffect(() => {
    setLoading(true);
    fetch(`${API_URL}`)
      .then(res => res.json())
      .then(data => setTodos(data))
      .finally(() => setLoading(false));
  }, []);

  // Add a new todo
  const addTodo = async () => {
    if (!newTodo.trim()) return;
    const res = await fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ title: newTodo, completed: false })
    });
    const todo = await res.json();
    setTodos([todo, ...todos]);
    setNewTodo('');
  };

  // Delete a todo
  const deleteTodo = async (id: number) => {
    await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
    setTodos(todos.filter(t => t.id !== id));
  };

  return (
    <div style={{ maxWidth: 400, margin: '2rem auto', padding: 20, border: '1px solid #ccc', borderRadius: 8 }}>
      <img src={computasLogo} alt="Computas AS Logo" style={{ display: 'block', margin: '0 auto 1rem', maxWidth: '100%' }} />
      <h2>TODO App</h2>
      <div style={{ display: 'flex', gap: 8 }}>
        <input
          value={newTodo}
          onChange={e => setNewTodo(e.target.value)}
          placeholder="Add a new todo"
        />
        <button onClick={addTodo}>Add</button>
      </div>
      {loading ? <p>Loading...</p> : null}
      <ul style={{ listStyle: 'none', padding: 0 }}>
        {todos.map(todo => (
          <li key={todo.id} style={{ display: 'flex', alignItems: 'center', gap: 8, marginTop: 10 }}>
            <span style={{ textDecoration: todo.completed ? 'line-through' : 'none' }}>{todo.title || todo.title}</span>
            <button onClick={() => deleteTodo(todo.id)} style={{ marginLeft: 'auto' }}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TodoApp;

