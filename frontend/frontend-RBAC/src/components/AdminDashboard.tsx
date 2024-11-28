import React, { useState, useEffect } from 'react';
import { userService } from '../services/userService';
import { User, UserRole } from '../types/User';

const AdminDashboard: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const userList = await userService.getAllUsers();
                const formattedUsers = userList.map((user: any) => ({
                    ...user,
                    username: user.username || 'Unknown',  // Handle empty username
                    name: user.name || 'No name provided',  // Handle null name
                    roles: user.roles.map((role: string) => UserRole[role as keyof typeof UserRole])
                }));

                setUsers(formattedUsers);
            } catch (error) {
                console.error('Failed to fetch users', error);
            }
        };

        fetchUsers();
    }, []);

    const handleRoleAssignment = async (username: string, role: UserRole) => {
        try {
            await userService.assignUserRole(username, role);
            const updatedUsers = users.map(user =>
                user.username === username
                    ? { ...user, roles: [...user.roles, role] }
                    : user
            );
            setUsers(updatedUsers);
        } catch (error) {
            console.error('Failed to assign role', error);
        }
    };

    return (
        <div>
            <h2>Admin Dashboard</h2>
            <table>
                <thead>
                <tr>
                    <th>Username</th>
                    <th>Name</th>
                    <th>Roles</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {users.map(user => (
                    <tr key={user.id}>
                        <td>{user.username}</td>
                        <td>{user.name || 'No name provided'}</td>
                        <td>{user.roles.join(', ')}</td>
                        <td>
                            <select
                                onChange={(e) =>
                                    handleRoleAssignment(user.username, e.target.value as UserRole)
                                }
                            >
                                <option value="">Assign Role</option>
                                <option value={UserRole.ADMIN}>Admin</option>
                                <option value={UserRole.MODERATOR}>Moderator</option>
                                <option value={UserRole.USER}>User</option>
                            </select>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};


export default AdminDashboard;
