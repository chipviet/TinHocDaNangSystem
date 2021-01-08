import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import production, {
  ProductionState
} from 'app/entities/production/production.reducer';
// prettier-ignore
import category, {
  CategoryState
} from 'app/entities/category/category.reducer';
// prettier-ignore
import brand, {
  BrandState
} from 'app/entities/brand/brand.reducer';
// prettier-ignore
import guarantee, {
  GuaranteeState
} from 'app/entities/guarantee/guarantee.reducer';
// prettier-ignore
import guaranteeProduction, {
  GuaranteeProductionState
} from 'app/entities/guarantee-production/guarantee-production.reducer';
// prettier-ignore
import promotion, {
  PromotionState
} from 'app/entities/promotion/promotion.reducer';
// prettier-ignore
import promotionProduction, {
  PromotionProductionState
} from 'app/entities/promotion-production/promotion-production.reducer';
// prettier-ignore
import cart, {
  CartState
} from 'app/entities/cart/cart.reducer';
// prettier-ignore
import cartProduction, {
  CartProductionState
} from 'app/entities/cart-production/cart-production.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly production: ProductionState;
  readonly category: CategoryState;
  readonly brand: BrandState;
  readonly guarantee: GuaranteeState;
  readonly guaranteeProduction: GuaranteeProductionState;
  readonly promotion: PromotionState;
  readonly promotionProduction: PromotionProductionState;
  readonly cart: CartState;
  readonly cartProduction: CartProductionState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  production,
  category,
  brand,
  guarantee,
  guaranteeProduction,
  promotion,
  promotionProduction,
  cart,
  cartProduction,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
