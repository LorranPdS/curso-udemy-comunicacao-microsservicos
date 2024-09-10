import UserRepository from "../repository/UserRepository";
import * as HttpStatus from "../../../config/constants/HttpStatus";

class UserService {

    async findByEmail(req) {
        try {
            const { email } = req.params;
            this.validateRequestData(email);
            let user = UserRepository.findByEmail(email);
            if (!user){

            }
            return {
                status: HttpStatus.SUCCESS,
                user: {
                    id: user.id,
                    name: user.name,
                    email: user.email,
                },
            };
        } catch (err) {
            return {
                status: err.status ? err.status : HttpStatus.INTERNAL_SERVER_ERROR,
                message: err.status,
            };
        }
    }
    validateRequestData(email) {
        if(!email) {
            throw new Error('User email was not informed.');
        }
    }
}

export default new UserService();