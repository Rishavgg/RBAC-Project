import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../../services/authservice.ts';
import { useAuth } from '../../contexts/AuthContext';

const Login: React.FC = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();
    const { login } = useAuth();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setErrorMessage(''); // Reset error message on new attempt
        try {
            const response = await authService.login(username, password);
            login(response.user); // Assuming response contains user and JWT

            // Store JWT correctly
            if (response.token) {
                localStorage.setItem('token', response.token); // Store the token properly
            }

            if (response.user.roles.includes('ADMIN')) {
                navigate('/admin/dashboard');
            } else if (response.user.roles.includes('MODERATOR')) {
                navigate('/moderator/dashboard');
            } else {
                navigate('/profile');
            }
        } catch (error) {
            console.error('Login failed', error);
            setErrorMessage('Login failed. Please check your credentials.');
        }
    };

    return (
        <div>
            <form onSubmit={handleLogin}>
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Username"
                />
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Password"
                />
                <button type="submit">Login</button>
            </form>
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
        </div>
    );
};

export default Login;
