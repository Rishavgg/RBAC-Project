import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/authservice.ts';
import { User, UserRole } from '../types/User.ts';

const Register: React.FC = () => {
    const [username, setUsername] = useState('');
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState<UserRole>(UserRole.USER);
    const navigate = useNavigate();

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const user: User = {
                username,
                name,
                roles: [role]
            };
            await authService.register(user, role);
            navigate('/login');
        } catch (error) {
            console.error('Registration failed', error);
        }
    };

    return (
        <div>
            <form onSubmit={handleRegister}>
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Username"
                    required
                />
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    placeholder="Name"
                    required
                />
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Password"
                    required
                />
                <select
                    value={role}
                    onChange={(e) => setRole(e.target.value as UserRole)}
                >
                    <option value={UserRole.USER}>User</option>
                    <option value={UserRole.MODERATOR}>Moderator</option>
                    <option value={UserRole.ADMIN}>Admin</option>
                </select>
                <button type="submit">Register</button>
            </form>
        </div>
    );
};

export default Register;