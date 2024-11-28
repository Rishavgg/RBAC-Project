import React, { useState, useEffect } from 'react';
import { userService } from '../services/userService.ts';
import { User } from '../types/User.ts';

const ModeratorDashboard: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const userList = await userService.getAllUsers();
                setUsers(userList);
            } catch (error) {
                console.error('Failed to fetch users', error);
            }
        };

        fetchUsers();
    }, []);

    return (
        <div>
            <h2>Moderator Dashboard</h2>
            <table>
                <thead>
                <tr>
                    <th>Username</th>
                    <th>Name</th>
                </tr>
                </thead>
                <tbody>
                {users.map(user => (
                    <tr key={user.id}>
                        <td>{user.username}</td>
                        <td>{user.name}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default ModeratorDashboard;