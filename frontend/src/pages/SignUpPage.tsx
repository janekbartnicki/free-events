import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { User } from "../contexts/UserContext";
import { useAlert } from "../contexts/AlertContext";

const SignUpPage: React.FC = () => {
  const showAlert = useAlert();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: '',
    name: '',
    password: '',
  });
  const [isLoading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setErrorMessage('');
    
    try {
      const url = 'http://localhost:8080/api/users/register';
      await axios.post<User>(url, formData, {
        headers: {
          'Content-Type': 'application/json'
        }
      })

      showAlert('Zarejestrowano!');
      navigate('/sign-in');
    } catch(e: any) {
      setErrorMessage(e.response.data);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <div className="flex items-center justify-center min-h-screen bg-base-200">
        <div className="card w-full max-w-md bg-base-100 shadow-xl relative">
          { isLoading && 
            <div className="absolute left-0 top-0 w-full h-full flex justify-center rounded-2xl align-middle bg-opacity-70 bg-slate-800">
              <span className="loading loading-spinner loading-lg text-white"></span>
            </div>
          }
          <div className="card-body">
            <h2 className="text-center text-2xl font-bold">Rejestracja</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-control w-full">
                <label className="label">
                  <span className="label-text">Email</span>
                </label>
                <input
                  type="email"
                  name="email"
                  placeholder="Podaj email"
                  className="input input-bordered w-full"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="form-control w-full">
                <label className="label">
                  <span className="label-text">Nazwa użytkownika</span>
                </label>
                <input
                  type="text"
                  name="name"
                  placeholder="Podaj nazwę użytkownika"
                  className="input input-bordered w-full"
                  value={formData.name}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="form-control w-full">
                <label className="label">
                  <span className="label-text">Hasło</span>
                </label>
                <input
                  type="password"
                  name="password"
                  placeholder="Podaj hasło"
                  className="input input-bordered w-full"
                  value={formData.password}
                  onChange={handleChange}
                  required
                />
              </div>
              <p className="mt-2">Masz już konto? <span className="underline cursor-pointer hover:text-primary" onClick={() => navigate('/sign-in')}>Zaloguj się</span>.</p>
              <div className="form-control mt-4">
                <button className="btn btn-primary w-full">Zarejestruj się</button>
              </div>
            </form>
          </div>
        </div>
      </div>
      { errorMessage && 
                <div role="alert" className="absolute bottom-0 right-0 m-5 w-96 alert alert-error">
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        className="h-6 w-6 shrink-0 stroke-current"
                        fill="none"
                        viewBox="0 0 24 24">
                        <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                    <span>{ errorMessage }</span>
                </div>
            }
    </>
  );
}

export default SignUpPage;